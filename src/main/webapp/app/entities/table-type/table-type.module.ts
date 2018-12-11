import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    TableTypeComponent,
    TableTypeDetailComponent,
    TableTypeUpdateComponent,
    TableTypeDeletePopupComponent,
    TableTypeDeleteDialogComponent,
    tableTypeRoute,
    tableTypePopupRoute
} from './';

const ENTITY_STATES = [...tableTypeRoute, ...tableTypePopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TableTypeComponent,
        TableTypeDetailComponent,
        TableTypeUpdateComponent,
        TableTypeDeleteDialogComponent,
        TableTypeDeletePopupComponent
    ],
    entryComponents: [TableTypeComponent, TableTypeUpdateComponent, TableTypeDeleteDialogComponent, TableTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndTableTypeModule {}
