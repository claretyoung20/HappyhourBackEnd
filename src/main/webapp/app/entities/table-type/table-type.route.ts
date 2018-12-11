import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TableType } from 'app/shared/model/table-type.model';
import { TableTypeService } from './table-type.service';
import { TableTypeComponent } from './table-type.component';
import { TableTypeDetailComponent } from './table-type-detail.component';
import { TableTypeUpdateComponent } from './table-type-update.component';
import { TableTypeDeletePopupComponent } from './table-type-delete-dialog.component';
import { ITableType } from 'app/shared/model/table-type.model';

@Injectable({ providedIn: 'root' })
export class TableTypeResolve implements Resolve<ITableType> {
    constructor(private service: TableTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tableType: HttpResponse<TableType>) => tableType.body));
        }
        return of(new TableType());
    }
}

export const tableTypeRoute: Routes = [
    {
        path: 'table-type',
        component: TableTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.tableType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'table-type/:id/view',
        component: TableTypeDetailComponent,
        resolve: {
            tableType: TableTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.tableType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'table-type/new',
        component: TableTypeUpdateComponent,
        resolve: {
            tableType: TableTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.tableType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'table-type/:id/edit',
        component: TableTypeUpdateComponent,
        resolve: {
            tableType: TableTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.tableType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tableTypePopupRoute: Routes = [
    {
        path: 'table-type/:id/delete',
        component: TableTypeDeletePopupComponent,
        resolve: {
            tableType: TableTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.tableType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
