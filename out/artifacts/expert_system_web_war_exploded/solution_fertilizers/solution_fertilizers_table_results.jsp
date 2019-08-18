<data class="data-result">
    <table class="table table-bordered" id="table-result">
        <thead>
        </thead>
        <tbody>
        <tr>
            <th class="pack" colspan="5"><fmt:message key="FERT_FORM_TABLE_RES_PACKAGES_TITLE"/> </th>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_AGR_SYST"/> </th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_FIELD"/> </th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_AREA"/> </th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_SOIL"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_DEPTH"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_BULK"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_CROP"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_PLAN_YIELD"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_PRED"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_PRED_DOZE"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_RES_PRED_YEILD"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_FERT"/></th>
            <td colspan="4"></td>
        </tr>
        <tr>
            <th rowspan="2" class="caption"><fmt:message key="FERT_FORM_TABLE_CHEM_COMP_DATA"/></th>
            <th><fmt:message key="FERT_FORM_TABLE_CHEM_COMP_H"/></th>
            <th><fmt:message key="FERT_FORM_TABLE_CHEM_COMP_PH"/></th>
            <th colspan="2"><fmt:message key="FERT_FORM_TABLE_CHEM_COMP_P"/></th>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_MICROELEMENTS"/></th>
            <th><fmt:message key="FERT_FORM_N_CAPS"/></th>
            <th><fmt:message key="FERT_FORM_PO_CAPS"/></th>
            <th><fmt:message key="FERT_FORM_KO_CAPS"/></th>
            <th><fmt:message key="FERT_FORM_TABLE_SOURCE"/></th>
        </tr>
        <tr>
            <th class="caption"><fmt:message key="FERT_FORM_TABLE_REMOVAL_COEFF"/></th>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th rowspan="2" class="caption"><fmt:message key="FERT_FORM_TABLE_USING_FROM_FERT"/></th>
            <td></td>
            <td></td>
            <td></td>
            <td rowspan="2"></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th rowspan="2" class="caption"><fmt:message key="FERT_FORM_TABLE_USING_FROM_SOIL"/></th>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <div id="tabs">
        <ul class="nav nav-tabs" style="border: none;">
            <li class="active"><a data-toggle="tab" href="#common-res"><fmt:message key="FERT_FORM_TABLE_COMMON_RES"/></a></li>
            <li class="presentation"><a data-toggle="tab" href="#res-description"><fmt:message key="FERT_FORM_TABLE_RES_DESCRIPTION"/></a></li>
        </ul>
    </div>
    <div class="tab-content">
        <div id="common-res" class="tab-pane fade in active">
            <table class="table table-bordered" id="common-res-table">
                <thead>
                <tr>
                    <th><fmt:message key="FERT_FORM_TABLE_MICROELEMENTS"/></th>
                    <th><fmt:message key="FERT_FORM_N"/></th>
                    <th><fmt:message key="FERT_FORM_PO"/></th>
                    <th><fmt:message key="FERT_FORM_KO"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="caption"><fmt:message key="FERT_FORM_SETT_REAL_POSSIBLE_PRODUCT"/></th>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <th class="caption"><fmt:message key="FERT_FORM_SETT_CROP_NEED_ADD_MICROEL"/></th>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <th class="res"><fmt:message key="FERT_FORM_SETT_MICROELEMENT_DOZE"/></th>
                    <th class="res"></th>
                    <th class="res"></th>
                    <th class="res"></th>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="res-description" class="tab-pane fade">
            <table class="table table-bordered" id="res-description-table">
                <thead>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_PARAN_NAMES"/></th>
                    <th colspan="2" class="fertilizers-color"><fmt:message key="FERT_FORM_N"/></th>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_PO"/></th>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_KO"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_REMOVAL_OF_MICROEL_BY_CROP"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_MASS"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td"></td>
                </tr>
                <tr>
                    <th class="fertilizers" rowspan="2"><fmt:message key="FERT_FORM_TABLE_AMOUNT_OF_MOVING_MICROELEMENT"/></th>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_AMOUNT_OF_H"/></th>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_AMOUNT_OF_N_IN_H"/></th>
                    <td rowspan="2" class="fertilizers-padding-td"></td>
                    <td rowspan="2" class="fertilizers-padding-td"></td>
                </tr>
                <tr>
                    <td class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_AVALIBLE_MIN_FORMS_OF_N"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td">-</td>
                    <td class="fertilizers-color-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_AMOUNT_OF_DIGSIBLE_MICROEL"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_LEVEL_OF_RPP"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td">-</td>
                    <td class="fertilizers-color-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_POSSIBLE_DIGSIBLE_N"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td">-</td>
                    <td class="fertilizers-padding-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_N_IN_SOIL_FROM_PRED"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td">-</td>
                    <td class="fertilizers-color-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_N_IN_SOIL"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td">-</td>
                    <td class="fertilizers-padding-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_N_IN_PRED"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td">-</td>
                    <td class="fertilizers-color-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_COMMON_AMOUNT_OF_MICROEL_FROM_SOIL"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                </tr>
                <tr>
                    <th class="fertilizers-color"><fmt:message key="FERT_FORM_TABLE_ADD_MICROEL"/></th>
                    <td colspan="2" class="fertilizers-color-td"></td>
                    <td class="fertilizers-color-td">-</td>
                    <td class="fertilizers-color-td">-</td>
                </tr>
                <tr>
                    <th class="fertilizers"><fmt:message key="FERT_FORM_TABLE_DOZE_OF_MICROEL"/></th>
                    <td colspan="2" class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                    <td class="fertilizers-padding-td"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</data>