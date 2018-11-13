import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Reservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { ReservationComponent } from './reservation.component';
import { ReservationDetailComponent } from './reservation-detail.component';
import { ReservationUpdateComponent } from './reservation-update.component';
import { ReservationDeletePopupComponent } from './reservation-delete-dialog.component';
import { IReservation } from 'app/shared/model/reservation.model';

@Injectable({ providedIn: 'root' })
export class ReservationResolve implements Resolve<IReservation> {
    constructor(private service: ReservationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reservation: HttpResponse<Reservation>) => reservation.body));
        }
        return of(new Reservation());
    }
}

export const reservationRoute: Routes = [
    {
        path: 'reservation',
        component: ReservationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.reservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reservation/:id/view',
        component: ReservationDetailComponent,
        resolve: {
            reservation: ReservationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.reservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reservation/new',
        component: ReservationUpdateComponent,
        resolve: {
            reservation: ReservationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.reservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reservation/:id/edit',
        component: ReservationUpdateComponent,
        resolve: {
            reservation: ReservationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.reservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reservationPopupRoute: Routes = [
    {
        path: 'reservation/:id/delete',
        component: ReservationDeletePopupComponent,
        resolve: {
            reservation: ReservationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.reservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
