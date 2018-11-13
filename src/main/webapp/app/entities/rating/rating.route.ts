import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Rating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';
import { RatingComponent } from './rating.component';
import { RatingDetailComponent } from './rating-detail.component';
import { RatingUpdateComponent } from './rating-update.component';
import { RatingDeletePopupComponent } from './rating-delete-dialog.component';
import { IRating } from 'app/shared/model/rating.model';

@Injectable({ providedIn: 'root' })
export class RatingResolve implements Resolve<IRating> {
    constructor(private service: RatingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((rating: HttpResponse<Rating>) => rating.body));
        }
        return of(new Rating());
    }
}

export const ratingRoute: Routes = [
    {
        path: 'rating',
        component: RatingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.rating.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rating/:id/view',
        component: RatingDetailComponent,
        resolve: {
            rating: RatingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.rating.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rating/new',
        component: RatingUpdateComponent,
        resolve: {
            rating: RatingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.rating.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rating/:id/edit',
        component: RatingUpdateComponent,
        resolve: {
            rating: RatingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.rating.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ratingPopupRoute: Routes = [
    {
        path: 'rating/:id/delete',
        component: RatingDeletePopupComponent,
        resolve: {
            rating: RatingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.rating.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
