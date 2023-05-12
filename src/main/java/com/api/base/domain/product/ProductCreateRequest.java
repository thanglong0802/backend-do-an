package com.api.base.domain.product;

import com.api.base.utils.enumerate.ProductStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Lob;
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
    @Lob
    private String nameProduct;

    @NotNull
    @ApiModelProperty(value = "Trạng thái sản phẩm")
    private ProductStatus status;

    @NotNull
    @ApiModelProperty(value = "Giá sản phẩm")
    private Double price;

    private Double promotionalPrice;

    @NotNull
    @ApiModelProperty(value = "Số lượng sản phẩm")
    private Integer quantity;

    @ApiModelProperty(value = "Mô tả sản phẩm")
    @Lob
    private String description;

    @ApiModelProperty(value = "Công dụng sản phẩm")
    @Lob
    private String use;

    @ApiModelProperty(value = "Nhà sản xuất")
    @Lob
    private String producer;

    @ApiModelProperty(value = "Nơi sản xuất")
    @Lob
    private String whereProduction;
}
