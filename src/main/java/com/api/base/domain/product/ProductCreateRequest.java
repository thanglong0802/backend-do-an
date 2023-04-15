package com.api.base.domain.product;

import com.api.base.utils.enumerate.ProductStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductCreateRequest {

    @NotNull
    @ApiModelProperty(value = "ID danh mục")
    private Long categoriesId;

    @NotBlank
    @ApiModelProperty(value = "Tên sản phẩm")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Trạng thái sản phẩm")
    private ProductStatus status;

    @NotBlank
    @ApiModelProperty(value = "Ảnh sản phẩm")
    private String thumbImg;

    @NotNull
    @ApiModelProperty(value = "Giá sản phẩm")
    private Double price;

    @NotNull
    @ApiModelProperty(value = "Số lượng sản phẩm")
    private Integer quantity;

    @ApiModelProperty(value = "Mô tả sản phẩm")
    private String description;

    @ApiModelProperty(value = "Công dụng sản phẩm")
    private String use;

    @ApiModelProperty(value = "Nhà sản xuất")
    private String producer;

    @ApiModelProperty(value = "Nơi sản xuất")
    private String whereProduction;
}
