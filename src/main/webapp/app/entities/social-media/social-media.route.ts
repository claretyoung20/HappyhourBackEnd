import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';
import { SocialMediaComponent } from './social-media.component';
import { SocialMediaDetailComponent } from './social-media-detail.component';
import { SocialMediaUpdateComponent } from './social-media-update.component';
import { SocialMediaDeletePopupComponent } from './social-media-delete-dialog.component';
import { ISocialMedia } from 'app/shared/model/social-media.model';

@Injectable({ providedIn: 'root' })
export class SocialMediaResolve implements Resolve<ISocialMedia> {
    constructor(private service: SocialMediaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((socialMedia: HttpResponse<SocialMedia>) => socialMedia.body));
        }
        return of(new SocialMedia());
    }
}

export const socialMediaRoute: Routes = [
    {
        path: 'social-media',
        component: SocialMediaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.socialMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'social-media/:id/view',
        component: SocialMediaDetailComponent,
        resolve: {
            socialMedia: SocialMediaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.socialMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'social-media/new',
        component: SocialMediaUpdateComponent,
        resolve: {
            socialMedia: SocialMediaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.socialMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'social-media/:id/edit',
        component: SocialMediaUpdateComponent,
        resolve: {
            socialMedia: SocialMediaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.socialMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const socialMediaPopupRoute: Routes = [
    {
        path: 'social-media/:id/delete',
        component: SocialMediaDeletePopupComponent,
        resolve: {
            socialMedia: SocialMediaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.socialMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
