package com.mitrais.bootcamp.view;

import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.common.dto.ATMSimulationResult;
import com.mitrais.bootcamp.common.dto.BaseScreenResponseData;
import com.mitrais.bootcamp.common.dto.ScreenRequestData;

public interface Screen {

    default ScreenCode getScreenCode() {
        return ScreenCode.WELCOME_SCREEN;
    }

    ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData);
}