package com.fzdkx.model.media.dto;

import com.fzdkx.model.common.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Data
public class MaterialListDto extends PageRequestDto{
    private Short isCollection;

    @Override
    public void checkParam() {
        super.checkParam();
        if (this.isCollection == null){
            this.isCollection = 0;
        }
    }
}
