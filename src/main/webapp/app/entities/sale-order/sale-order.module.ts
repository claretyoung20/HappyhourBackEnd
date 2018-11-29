import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    SaleOrderComponent,
    SaleOrderDetailComponent,
    SaleOrderUpdateComponent,
    SaleOrderDeletePopupComponent,
    SaleOrderDeleteDialogComponent,
    saleOrderRoute,
    saleOrderPopupRoute
} from './';

const ENTITY_STATES = [...saleOrderRoute, ...saleOrderPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SaleOrderComponent,
        SaleOrderDetailComponent,
        SaleOrderUpdateComponent,
        SaleOrderDeleteDialogComponent,
        SaleOrderDeletePopupComponent
    ],
    entryComponents: [SaleOrderComponent, SaleOrderUpdateComponent, SaleOrderDeleteDialogComponent, SaleOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndSaleOrderModule {}
