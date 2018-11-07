import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    TimeManagerComponent,
    TimeManagerDetailComponent,
    TimeManagerUpdateComponent,
    TimeManagerDeletePopupComponent,
    TimeManagerDeleteDialogComponent,
    timeManagerRoute,
    timeManagerPopupRoute
} from './';

const ENTITY_STATES = [...timeManagerRoute, ...timeManagerPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TimeManagerComponent,
        TimeManagerDetailComponent,
        TimeManagerUpdateComponent,
        TimeManagerDeleteDialogComponent,
        TimeManagerDeletePopupComponent
    ],
    entryComponents: [TimeManagerComponent, TimeManagerUpdateComponent, TimeManagerDeleteDialogComponent, TimeManagerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndTimeManagerModule {}
