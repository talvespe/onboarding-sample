/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myspace;

import java.math.BigDecimal;
import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.submarine.rest.quarkus.DMNResult;
import org.kie.dmn.submarine.rest.quarkus.DMNSubmarineQuarkus;

import com.myspace.payroll.Payroll;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Path("/vacationDays")
@Api(description = "Vaction days service")
public class VacationDaysDMNEndpoint {

    static final DMNRuntime dmnRuntime = DMNSubmarineQuarkus.createGenericDMNRuntime();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Calculates vacation days for given employee")
    public Payroll calculateVactionDays(@ApiParam(value = "data model representing employee to be used") Payroll payrollInput) {
        DMNResult evaluate = DMNSubmarineQuarkus.evaluate(dmnRuntime, "vacationDays", Collections.singletonMap("employee", payrollInput.getEmployee()));
        Payroll payroll = new Payroll();
        
        payroll.setVacationDays((BigDecimal) evaluate.getDmnContext().get("vacationDays"));
        return payroll;
    }

}
