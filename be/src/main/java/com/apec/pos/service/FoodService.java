package com.apec.pos.service;

import com.apec.pos.dto.FoodDto.*;
import com.apec.pos.entity.FoodEntity;
import com.apec.pos.entity.ToppingEntity;
import com.apec.pos.repository.FoodRepository;
import com.apec.pos.repository.ToppingRepository;
import com.apec.pos.service.serviceInterface.FoodInterface;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class FoodService extends BaseService<FoodRepository, FoodEntity, Integer> implements FoodInterface {

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    FoodRepository getRepository() {
        // TODO Auto-generated method stub
        return foodRepository;
    }

    @Override
    public List<FoodRecommendDto> getFoodRecommand() {
        List<FoodEntity> foodEntitys = foodRepository.getTopFood();
        List<FoodRecommendDto> foodRecommanDtos = new ArrayList<FoodRecommendDto>();
        for (FoodEntity x : foodEntitys) {
            String nameRes = x.getRestaurantEntity().getRestaurantName();
            //convert topping
            Gson gson = new Gson();
//            List<ToppingResponse> toppingResponses = new ArrayList<>();
//            for (ToppingEntity y:x.getRestaurantEntity().getToppingEntityList()
//            ) {
//                ToppingResponse toppingResponse= ToppingResponse.builder()
//                        .id(y.getId())
//                        .title(y.getTitle())
//                        .requi(y.getRequi())
//                        .itemList(gson.fromJson(y.getItems(),new TypeToken<List<Item>>(){}.getType()))
//                        .build();
//                toppingResponses.add(toppingResponse);
//            }
            FoodRecommendDto temp =
                    new FoodRecommendDto(
                            x.getId(),
                            x.getFoodName(),
                            x.getPrice(),
                            x.getDetail(),
                            nameRes,
                            x.getImgFood(),
                            x.getCreateBy(),
                            x.getCreateDate(),
                            x.getQuantityPurchased(),
                            x.getTypeFoodEntityId(),
                            x.getRestaurantEntityId(),
                            x.getStatus(),
                            x.getRestaurantEntity().getDistance(),
//                            toppingResponses,
                            x.getTypeFoodEntity().getNameType()
                    );

            foodRecommanDtos.add(temp);
        }
        return foodRecommanDtos;
    }

    @Override
    public FoodRecommendDto addFood(AddFoodRequest addFoodRequest) {

        String imgFood = "";
        if (addFoodRequest.getImgFood() != null) {
            try {
                imgFood = fileUploadService.uploadFile(addFoodRequest.getImgFood().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<ToppingEntity> listToppingTemp = new ArrayList<>();

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setDetail(addFoodRequest.getDetail());
        foodEntity.setFoodName(addFoodRequest.getFoodName());
        foodEntity.setImgFood(imgFood);
        foodEntity.setPrice(addFoodRequest.getPrice());
        foodEntity.setRestaurantEntityId(addFoodRequest.getRestaurantEntityId());
        foodEntity.setTypeFoodEntityId(addFoodRequest.getTypeFoodEntityId());
        foodEntity.setQuantityPurchased(0);

        foodEntity = foodRepository.insert(foodEntity);

        FoodRecommendDto foodRecommanDto = new FoodRecommendDto();
        foodRecommanDto.setCreateAt(foodEntity.getCreateDate());
        foodRecommanDto.setCreateBy(foodEntity.getCreateBy());
        foodRecommanDto.setFoodName(foodEntity.getFoodName());
        foodRecommanDto.setPrice(foodEntity.getPrice());
        foodRecommanDto.setNameRestaurantFood(foodEntity.getFoodName());
        foodRecommanDto.setImgFood(foodEntity.getImgFood());
        foodRecommanDto.setTypeFoodEntityId(foodEntity.getTypeFoodEntityId());
        foodRecommanDto.setRestaurantEntityId(foodEntity.getRestaurantEntityId());
        foodRecommanDto.setStatus(foodEntity.getStatus());
        return foodRecommanDto;
    }

    @Override
    public List<FoodEntity> findFoodOnType(FoodEntity foodEntity) {
        return foodRepository.findFoodOnType(foodEntity);
    }

    @Override
    public List<FoodRecommendDto> searchFood(String searchString) {
        List<FoodEntity> foodEntity = foodRepository.findFoodByKey(searchString);
        List<FoodRecommendDto> foodSearchRespons = new ArrayList<>();
        for (FoodEntity x : foodEntity) {
            String nameRes = x.getRestaurantEntity().getRestaurantName();
            Double distance = x.getRestaurantEntity().getDistance();
            //convert topping
            Gson gson = new Gson();
//            List<ToppingResponse> toppingResponses = new ArrayList<>();
//            for (ToppingEntity y:x.getRestaurantEntity().getToppingEntityList()
//            ) {
//                ToppingResponse toppingResponse= ToppingResponse.builder()
//                        .id(y.getId())
//                        .title(y.getTitle())
//                        .requi(y.getRequi())
//                        .itemList(gson.fromJson(y.getItems(),new TypeToken<List<Item>>(){}.getType()))
//                        .build();
//                toppingResponses.add(toppingResponse);
//            }
            FoodRecommendDto temp = new FoodRecommendDto(
                    x.getId(),
                    x.getFoodName(),
                    x.getPrice(),
                    x.getDetail(),
                    nameRes,
                    x.getImgFood(),
                    x.getCreateBy(),
                    x.getCreateDate(),
                    x.getQuantityPurchased(),
                    x.getTypeFoodEntityId(),
                    x.getRestaurantEntityId(),
                    x.getStatus(),
                    x.getRestaurantEntity().getDistance(),
//                    toppingResponses,
                    x.getTypeFoodEntity().getNameType()
            );
            foodSearchRespons.add(temp);
        }
        return foodSearchRespons;
    }

    @Override
    public FoodRecommendDto getDetailFood(Integer id) {
        FoodEntity x = foodRepository.findOne(id);
        if (x==null){
            return null;
        }
        String nameRes = x.getRestaurantEntity().getRestaurantName();
        //convert topping
        Gson gson = new Gson();
//        List<ToppingResponse> toppingResponses = new ArrayList<>();
//        for (ToppingEntity y:x.getRestaurantEntity().getToppingEntityList()
//        ) {
//            ToppingResponse toppingResponse= ToppingResponse.builder()
//                    .id(y.getId())
//                    .title(y.getTitle())
//                    .requi(y.getRequi())
//                    .itemList(gson.fromJson(y.getItems(),new TypeToken<List<Item>>(){}.getType()))
//                    .build();
//            toppingResponses.add(toppingResponse);
//        }

        FoodRecommendDto temp = new FoodRecommendDto(
                x.getId(),
                x.getFoodName(),
                x.getPrice(),
                x.getDetail(),
                nameRes,
                x.getImgFood(),
                x.getCreateBy(),
                x.getCreateDate(),
                x.getQuantityPurchased(),
                x.getTypeFoodEntityId(),
                x.getRestaurantEntityId(),
                x.getStatus(),
                x.getRestaurantEntity().getDistance(),
//                toppingResponses,
                x.getTypeFoodEntity().getNameType()
        );
        return temp;
    }

    @Override
    public FoodResponseAdmin paging(int pageSize, int pageIndex) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        List<FoodRecommendDto> foodRecommanDtos = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepository.paging(pageRequest);
        for (FoodEntity x : foodEntities) {
            Gson gson = new Gson();

            String restaurantName = (x.getRestaurantEntity() != null) ? x.getRestaurantEntity().getRestaurantName() : "";
            Double restaurantDistance = (x.getRestaurantEntity() != null) ? x.getRestaurantEntity().getDistance() : null;

            FoodRecommendDto data = new FoodRecommendDto(
                    x.getId(),
                    x.getFoodName(),
                    x.getPrice(),
                    x.getDetail(),
                    restaurantName,
                    x.getImgFood(),
                    x.getCreateBy(),
                    x.getCreateDate(),
                    x.getQuantityPurchased(),
                    x.getTypeFoodEntityId(),
                    x.getRestaurantEntityId(),
                    x.getStatus(),
                    restaurantDistance, // null-safe distance
                    x.getTypeFoodEntity().getNameType()
            );

            data.setStatus(x.getStatus());
            data.setCreateAt(x.getCreateDate());
            data.setCreateBy(x.getCreateBy());

            foodRecommanDtos.add(data);
        }

        FoodResponseAdmin foodResponseAdmin = new FoodResponseAdmin(foodRepository.countAll(), foodRecommanDtos);
        return foodResponseAdmin;
    }

    @Override
    public FoodRecommendDto updateFood(UpdateFood addFoodRequest) throws IOException {
        String imgFood = null;
        if (addFoodRequest.getImgFood() != null) {
            imgFood = fileUploadService.uploadFile(addFoodRequest.getImgFood().getBytes());
        }
        FoodEntity foodEntity = foodRepository.findOne(addFoodRequest.getId());
        if (foodEntity==null){
            return null;
        }
        if (addFoodRequest.getDetail() != null)
            foodEntity.setDetail(addFoodRequest.getDetail());
        if (addFoodRequest.getFoodName() != null)
            foodEntity.setFoodName(addFoodRequest.getFoodName());
        if (addFoodRequest.getImgFood() != null)
            foodEntity.setImgFood(imgFood);
        if (addFoodRequest.getPrice() != null)
            foodEntity.setPrice(addFoodRequest.getPrice());
        if (addFoodRequest.getRestaurantEntityId() != null)
            foodEntity.setRestaurantEntityId(addFoodRequest.getRestaurantEntityId());
        if (addFoodRequest.getRestaurantEntityId() != null)
            foodEntity.setTypeFoodEntityId(addFoodRequest.getTypeFoodEntityId());
        foodEntity = foodRepository.update(foodEntity);

        FoodRecommendDto foodRecommanDto = new FoodRecommendDto();
        foodRecommanDto.setCreateAt(foodEntity.getCreateDate());
        foodRecommanDto.setCreateBy(foodEntity.getCreateBy());
        foodRecommanDto.setFoodName(foodEntity.getFoodName());
        foodRecommanDto.setPrice(foodEntity.getPrice());
        foodRecommanDto.setNameRestaurantFood(foodEntity.getFoodName());
        foodRecommanDto.setImgFood(foodEntity.getImgFood());
        foodRecommanDto.setTypeFoodEntityId(foodEntity.getTypeFoodEntityId());
        foodRecommanDto.setRestaurantEntityId(foodEntity.getRestaurantEntityId());
        foodRecommanDto.setStatus(foodEntity.getStatus());

        return foodRecommanDto;
    }

    @Override
    public String updateStatusFood(Integer id, Boolean status) {
        FoodEntity foodEntity = foodRepository.findOne(id);
        if (foodEntity==null){
            return null;
        }
        foodEntity.setStatus(status);
        return "Đã Sửa status";
    }

    @Override
    public String deleteFood(Integer id) {
        foodRepository.delete(id);
        return "Đã xóa";
    }

    @Override
    public List<Integer> MuiltiDelete(Set<Integer> ids) {
        List<Integer> listId = new ArrayList<>();
        for (Integer x : ids) {
            int temp = foodRepository.delete(x);
            listId.add(temp);
        }
        return listId;
    }

    @Override
    public void multipartAddFood(List<AddMultipartFood> addFoodRequests) throws IOException {
        for (AddMultipartFood x:
             addFoodRequests) {
            //
            byte[] bytes = null;
            try(var stream = new URL(x.getImgFood()).openStream()){
                 bytes = stream.readAllBytes();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String img=fileUploadService.uploadFile(bytes);

            FoodEntity foodEntity = FoodEntity.builder()
                    .foodName(x.getFoodName())
                    .detail(x.getDetail())
                    .price(x.getPrice())
                    .imgFood(img)
                    .typeFoodEntityId(x.getTypeFoodEntityId())
                    .restaurantEntityId(x.getRestaurantEntityId())
                    .build();
            foodRepository.insert(foodEntity);
        }

    }


}
