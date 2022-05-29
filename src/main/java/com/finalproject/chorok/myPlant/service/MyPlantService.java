package com.finalproject.chorok.myPlant.service;

import com.finalproject.chorok.common.Image.Image;
import com.finalproject.chorok.common.Image.ImageRepository;
import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.*;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import com.finalproject.chorok.plant.repository.PlantRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MyPlantService {

    private final MyPlantRepository myPlantRepository;
    private final TodoRepository todoRepository;
    private final PlantRepository plantRepository;
    private final PlantPlaceRepository plantPlaceRepository;
    private final CommUtils commUtils;
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    //식물 등록할 때, 투두리스트를 처음에 자동으로 저장해줌.
    @Transactional
    public String addMyPlant(MyPlantRequestDto myPlantRequestDto, String plantPlace, User user) {
        MyPlant myPlant = new MyPlant(myPlantRequestDto, plantPlace, user);
        myPlantRepository.save(myPlant);
        todoRepository.save(new Todo("물주기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("영양제", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("분갈이", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("잎닦기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("환기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        return "내식물 등록완료";

    }

    //다시 만들기
    public List<AllMyPlantResponseDto> getAllMyPlant(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<MyPlant> myPlants = myPlantRepository.findAllByUser(user);
        List<AllMyPlantResponseDto> allMyPlantResponseDtos = new ArrayList<>();
        for (MyPlant myPlant : myPlants) {
            AllMyPlantResponseDto allMyPlantResponseDto = new AllMyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getMyPlantName(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantPlace(),
                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName()

            );
            allMyPlantResponseDtos.add(allMyPlantResponseDto);
        }
        return allMyPlantResponseDtos;
    }

    //나의 식물들과 그에딸린 모든 투두들 보기
    public List<MyPlantResponseDto> getMyPlant(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<MyPlant> myPlants = myPlantRepository.findAllByUser(user);
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        //TodoOnlyResponseDto에 값 넣어주기
        List<Todo> todos = todoRepository.findAllByUser(userDetails.getUser());
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();

        for (Todo todo : todos) {
            LocalDate thatDay = todoRepository.findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(user, todo.getMyPlant(), true, todo.getWorkType()).get().getLastWorkTime();
            if (thatDay == null) {
                thatDay = todo.getMyPlant().getStartDay();
            }

            TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                    todo.getTodoNo(),
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getWorkType(),
                    todo.getLastWorkTime(),
                    (int) (LocalDate.now().toEpochDay() - thatDay.toEpochDay()),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }

        return getMyPlantResponseDtos(myPlants, myPlantResponseDtos, todoOnlyResponseDtos);
    }

    private List<MyPlantResponseDto> getMyPlantResponseDtos(List<MyPlant> myPlants, List<MyPlantResponseDto> myPlantResponseDtos, List<TodoOnlyResponseDto> todoOnlyResponseDtos) {
        for (MyPlant myPlant : myPlants) {
            MyPlantResponseDto myPlantResponseDto = new MyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getPlantNo(),
                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName(),
                    myPlant.getMyPlantPlace(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    myPlant.getStartDay(),
                    myPlant.getEndDay(),
                    todoOnlyResponseDtos.stream().filter(h -> h.getMyPlantNo().equals(myPlant.getMyPlantNo())).collect(Collectors.toList()));
            myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }

    //날짜별로 투두 맞는것 모두
    public List<MyPlantResponseDto> getMyPlantForTodo(UserDetailsImpl userDetails) {
        //유저별 모든 식물 찾아오기
        User user = userDetails.getUser();

        List<MyPlant> myPlants = myPlantRepository.findAllByUser(user);
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        //todoResponseDto에 오늘에 해당하는 todo 넣어주기
        //작업수행여부에 상관없이 다 가져와서 보여줌.
        List<Todo> todos = todoRepository.findAllByUserAndTodoTime(userDetails.getUser(), LocalDate.now());
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();

        for (Todo todo : todos) {
            try {

                Optional<Todo> todo2 = todoRepository.findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByTodoTimeDesc(user, todo.getMyPlant(), true, todo.getWorkType());
                LocalDate thatDay = todo2.get().getTodoTime();
                TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                        todo.getTodoNo(),
                        todo.getMyPlant().getMyPlantNo(),
                        todo.getWorkType(),
                        todo.getLastWorkTime(),
                        //워크타입별 스테이터스가 true인 값과 오늘의 날짜차이. 즉, 몇일이 지났는지.

                        (int) (LocalDate.now().toEpochDay() - thatDay.toEpochDay()),
                        todo.isStatus()
                );
                todoOnlyResponseDtos.add(todoOnlyResponseDto);

            } catch (Exception e) {
                LocalDate thatDay = myPlantRepository.findByMyPlantNo(todo.getMyPlant().getMyPlantNo()).getStartDay();
                TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                        todo.getTodoNo(),
                        todo.getMyPlant().getMyPlantNo(),
                        todo.getWorkType(),
                        todo.getLastWorkTime(),
                        //워크타입별 스테이터스가 true인 값과 오늘의 날짜차이. 즉, 몇일이 지났는지.

                        (int) (LocalDate.now().toEpochDay() - thatDay.toEpochDay()),
                        todo.isStatus()
                );
                todoOnlyResponseDtos.add(todoOnlyResponseDto);
            }


        }
        for (MyPlant myPlant : myPlants) {
            MyPlantResponseDto myPlantResponseDto = new MyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getPlantNo(),
                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName(),
                    myPlant.getMyPlantPlace(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    myPlant.getStartDay(),
                    myPlant.getEndDay(),
                    todoOnlyResponseDtos.stream().filter(h -> h.getMyPlantNo().equals(myPlant.getMyPlantNo())).collect(Collectors.toList()));
            myPlantResponseDtos.add(myPlantResponseDto);
        }
        return myPlantResponseDtos;
    }


    //나의 식물 수정하기
    public MyPlant updateMyPlant(MyPlantUpdateRequestDto myPlantUpdateRequestDto, Long myPlantNo, UserDetailsImpl userDetails) {
        MyPlant myPlant = myPlantRepository.findByMyPlantNoAndUser(myPlantNo, userDetails.getUser());

        myPlant.update(myPlantUpdateRequestDto);
        return myPlant;
    }

    //장소별 식물보기
    public MyPlantForPlaceResponseDto getMyPlantsforPlace(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<MyPlant> myPlants = myPlantRepository.findAllByUser(user);
        List<MyPlantForPlaceListResponseDto> myPlantForPlaceListResponseDtos = new ArrayList<>();

        //플랜트플레이스 리스트 가져오기
        List<PlantPlace> plantPlaceList = plantPlaceRepository.findAll();
        List<String> placeList = new ArrayList<>();
        for (PlantPlace plantPlace : plantPlaceList) {
            String place = plantPlace.getPlantPlace();
            placeList.add(place);
        }

        for (int j = 0; j < myPlants.size(); j++) {
            MyPlantForPlaceListResponseDto myPlantForPlaceListResponseDto = new MyPlantForPlaceListResponseDto(
                    myPlants.get(j).getMyPlantNo(),
                    myPlants.get(j).getPlantNo(),
                    myPlants.get(j).getMyPlantPlace(),
                    plantRepository.findByPlantNo(myPlants.get(j).getPlantNo()).getPlantName(),
                    myPlants.get(j).getMyPlantImgUrl(),
                    myPlants.get(j).getMyPlantName()
            );
            myPlantForPlaceListResponseDtos.add(myPlantForPlaceListResponseDto);
        }
        MyPlantForPlaceResponseDto myPlantForPlaceResponseDto = new MyPlantForPlaceResponseDto(
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(0))).collect(Collectors.toList()),
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(1))).collect(Collectors.toList()),
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(2))).collect(Collectors.toList()),
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(3))).collect(Collectors.toList()),
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(4))).collect(Collectors.toList()),
                myPlantForPlaceListResponseDtos.stream().filter(h -> h.getMyPlantPlace().equals(placeList.get(5))).collect(Collectors.toList())
        );
        return myPlantForPlaceResponseDto;
    }

    /*
     * 2022-05-19 추가 기능
     * 김주호
     * 내식물번호를 받아서 식물하나 정보 반환
     */
    public MyOnePlantResponseDto findMyPlant(Long myPlantNo) {

        MyPlant myPlant = myPlantRepository.findById(myPlantNo).orElseThrow(
                () -> new NullPointerException("해당 나의식물번호가 존재하지 않습니다.")

        );
        Plant plant = plantRepository.findById(myPlant.getPlantNo()).orElseThrow(
                () -> new NullPointerException("해당 식물번호가 존재하지 않습니다.")
        );
        return new MyOnePlantResponseDto(

                myPlant.getMyPlantNo(),
                myPlant.getMyPlantImgUrl(),
                myPlant.getMyPlantPlace(),
                myPlant.getMyPlantName(),
                myPlant.getPlantNo(),
                plant.getPlantName()

        );

    }

    /*
     *2022-05-19 추가기능
     *최은아
     * 내식물 삭제
     */
    @Transactional
    public HashMap<String, String> delMyPlant(Long myPlantNo, UserDetailsImpl userDetails) {
        myPlantRepository.deleteMyPlantByAndUserAndAndMyPlantNo(userDetails.getUser(), myPlantNo);
        return commUtils.responseHashMap(HttpStatus.OK);
    }

    //내 식물 수정
    @Transactional
    public String updateMyPlant(Long myPlantNo, String myPlantName, String myPlantPlaceCode, MultipartFile multipartFile, String originalUrl, UserDetailsImpl userDetails) {
        MyPlant myPlant = myPlantRepository.findByMyPlantNo(myPlantNo);
        Image image = imageRepository.findByImageUrl(myPlant.getMyPlantImgUrl());
        if (userDetails.getUser().getUserId().equals(myPlant.getUser().getUserId())) {
            try {
                //originalUrl이 널값일때->멀티파트파일이 있을때
//            if (originalUrl == null || originalUrl.equals(""))
                if (!multipartFile.isEmpty() && image != null) {
                    //사진삭제
                    System.out.println("일번");
                    s3Uploader.deleteImage(image.getFilename());
                    imageRepository.deleteByImageUrl(myPlant.getMyPlantImgUrl());
                    String myPlantImgUrl = s3Uploader.upload(multipartFile, "static");
                    myPlant.setMyPlantName(myPlantName);
                    myPlant.setMyPlantImgUrl(myPlantImgUrl);
                    myPlant.setMyPlantPlace(plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace());
                    myPlantRepository.save(myPlant);
                }
                if (!multipartFile.isEmpty() && image == null) {
                    System.out.println("이번");
                    String myPlantImgUrl = s3Uploader.upload(multipartFile, "static");
                    myPlant.setMyPlantName(myPlantName);
                    myPlant.setMyPlantImgUrl(myPlantImgUrl);
                    myPlant.setMyPlantPlace(plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace());
                    myPlantRepository.save(myPlant);
                }
                //멀티파트파일이 날라는오는데 비어있을때
                if (multipartFile.isEmpty()) {

                    myPlant.setMyPlantName(myPlantName);
                    myPlant.setMyPlantImgUrl(originalUrl);
                    myPlant.setMyPlantPlace(plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace());
                    myPlantRepository.save(myPlant);
                }


                //멀티파트파일이 날라는오는데 비어있을때
                if (multipartFile.isEmpty() || multipartFile == null) {
                    System.out.println("삼번ㅋ");

                    myPlant.setMyPlantName(myPlantName);
                    myPlant.setMyPlantImgUrl(originalUrl);
                    myPlant.setMyPlantPlace(plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace());
                    myPlantRepository.save(myPlant);
                }


                return "멀티파트파일로 저장완료";

            } catch (NullPointerException e) {
                //멀티파트가 null일때니까, originalImgurl로 간다.
                System.out.println("사번");
                System.out.println("오리지날 유알엘은" + originalUrl);
                myPlant.setMyPlantName(myPlantName);
                myPlant.setMyPlantImgUrl(originalUrl);
                myPlant.setMyPlantPlace(plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace());
                myPlantRepository.save(myPlant);
                return "오리지날유알엘로 저장완료";
            } catch (IOException e) {
                e.printStackTrace();
                return "s3업로드오류용에러메세지";

            } catch (Exception e) {
                return "나의 식물이 아닙니다";
            }
        } else return "내 식물이 아닙니다";
    }

}


