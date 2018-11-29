import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    BookTableComponent,
    BookTableDetailComponent,
    BookTableUpdateComponent,
    BookTableDeletePopupComponent,
    BookTableDeleteDialogComponent,
    bookTableRoute,
    bookTablePopupRoute
} from './';

const ENTITY_STATES = [...bookTableRoute, ...bookTablePopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BookTableComponent,
        BookTableDetailComponent,
        BookTableUpdateComponent,
        BookTableDeleteDialogComponent,
        BookTableDeletePopupComponent
    ],
    entryComponents: [BookTableComponent, BookTableUpdateComponent, BookTableDeleteDialogComponent, BookTableDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndBookTableModule {}
