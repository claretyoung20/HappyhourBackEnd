import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    HappyOrderComponent,
    HappyOrderDetailComponent,
    HappyOrderUpdateComponent,
    HappyOrderDeletePopupComponent,
    HappyOrderDeleteDialogComponent,
    happyOrderRoute,
    happyOrderPopupRoute
} from './';

const ENTITY_STATES = [...happyOrderRoute, ...happyOrderPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HappyOrderComponent,
        HappyOrderDetailComponent,
        HappyOrderUpdateComponent,
        HappyOrderDeleteDialogComponent,
        HappyOrderDeletePopupComponent
    ],
    entryComponents: [HappyOrderComponent, HappyOrderUpdateComponent, HappyOrderDeleteDialogComponent, HappyOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndHappyOrderModule {}
