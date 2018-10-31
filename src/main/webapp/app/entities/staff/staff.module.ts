import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import { HappybourBackEndAdminModule } from 'app/admin/admin.module';
import {
    StaffComponent,
    StaffDetailComponent,
    StaffUpdateComponent,
    StaffDeletePopupComponent,
    StaffDeleteDialogComponent,
    staffRoute,
    staffPopupRoute
} from './';

const ENTITY_STATES = [...staffRoute, ...staffPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, HappybourBackEndAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StaffComponent, StaffDetailComponent, StaffUpdateComponent, StaffDeleteDialogComponent, StaffDeletePopupComponent],
    entryComponents: [StaffComponent, StaffUpdateComponent, StaffDeleteDialogComponent, StaffDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndStaffModule {}
