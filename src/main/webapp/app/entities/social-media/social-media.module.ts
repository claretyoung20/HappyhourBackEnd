import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HappybourBackEndSharedModule } from 'app/shared';
import {
    SocialMediaComponent,
    SocialMediaDetailComponent,
    SocialMediaUpdateComponent,
    SocialMediaDeletePopupComponent,
    SocialMediaDeleteDialogComponent,
    socialMediaRoute,
    socialMediaPopupRoute
} from './';

const ENTITY_STATES = [...socialMediaRoute, ...socialMediaPopupRoute];

@NgModule({
    imports: [HappybourBackEndSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SocialMediaComponent,
        SocialMediaDetailComponent,
        SocialMediaUpdateComponent,
        SocialMediaDeleteDialogComponent,
        SocialMediaDeletePopupComponent
    ],
    entryComponents: [SocialMediaComponent, SocialMediaUpdateComponent, SocialMediaDeleteDialogComponent, SocialMediaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndSocialMediaModule {}
