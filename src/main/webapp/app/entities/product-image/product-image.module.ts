import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    ProductImageComponent,
    ProductImageDetailComponent,
    ProductImageUpdateComponent,
    ProductImageDeletePopupComponent,
    ProductImageDeleteDialogComponent,
    productImageRoute,
    productImagePopupRoute
} from './';

const ENTITY_STATES = [...productImageRoute, ...productImagePopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductImageComponent,
        ProductImageDetailComponent,
        ProductImageUpdateComponent,
        ProductImageDeleteDialogComponent,
        ProductImageDeletePopupComponent
    ],
    entryComponents: [
        ProductImageComponent,
        ProductImageUpdateComponent,
        ProductImageDeleteDialogComponent,
        ProductImageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndProductImageModule {}
