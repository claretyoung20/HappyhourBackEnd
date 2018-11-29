import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    ContactUsComponent,
    ContactUsDetailComponent,
    ContactUsUpdateComponent,
    ContactUsDeletePopupComponent,
    ContactUsDeleteDialogComponent,
    contactUsRoute,
    contactUsPopupRoute
} from './';

const ENTITY_STATES = [...contactUsRoute, ...contactUsPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactUsComponent,
        ContactUsDetailComponent,
        ContactUsUpdateComponent,
        ContactUsDeleteDialogComponent,
        ContactUsDeletePopupComponent
    ],
    entryComponents: [ContactUsComponent, ContactUsUpdateComponent, ContactUsDeleteDialogComponent, ContactUsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndContactUsModule {}
