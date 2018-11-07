import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TimeManager } from 'app/shared/model/time-manager.model';
import { TimeManagerService } from './time-manager.service';
import { TimeManagerComponent } from './time-manager.component';
import { TimeManagerDetailComponent } from './time-manager-detail.component';
import { TimeManagerUpdateComponent } from './time-manager-update.component';
import { TimeManagerDeletePopupComponent } from './time-manager-delete-dialog.component';
import { ITimeManager } from 'app/shared/model/time-manager.model';

@Injectable({ providedIn: 'root' })
export class TimeManagerResolve implements Resolve<ITimeManager> {
    constructor(private service: TimeManagerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((timeManager: HttpResponse<TimeManager>) => timeManager.body));
        }
        return of(new TimeManager());
    }
}

export const timeManagerRoute: Routes = [
    {
        path: 'time-manager',
        component: TimeManagerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.timeManager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-manager/:id/view',
        component: TimeManagerDetailComponent,
        resolve: {
            timeManager: TimeManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.timeManager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-manager/new',
        component: TimeManagerUpdateComponent,
        resolve: {
            timeManager: TimeManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.timeManager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time-manager/:id/edit',
        component: TimeManagerUpdateComponent,
        resolve: {
            timeManager: TimeManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.timeManager.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeManagerPopupRoute: Routes = [
    {
        path: 'time-manager/:id/delete',
        component: TimeManagerDeletePopupComponent,
        resolve: {
            timeManager: TimeManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.timeManager.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
