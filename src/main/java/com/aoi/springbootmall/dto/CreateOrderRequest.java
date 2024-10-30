package com.aoi.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {

    //為這個 List 在 Dto 新增 BuyItem class 作為 object
    @NotEmpty
    private List<BuyItem> buyItemList;

    public @NotEmpty List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(@NotEmpty List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
