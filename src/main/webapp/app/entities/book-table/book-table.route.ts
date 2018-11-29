import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BookTable } from 'app/shared/model/book-table.model';
import { BookTableService } from './book-table.service';
import { BookTableComponent } from './book-table.component';
import { BookTableDetailComponent } from './book-table-detail.component';
import { BookTableUpdateComponent } from './book-table-update.component';
import { BookTableDeletePopupComponent } from './book-table-delete-dialog.component';
import { IBookTable } from 'app/shared/model/book-table.model';

@Injectable({ providedIn: 'root' })
export class BookTableResolve implements Resolve<IBookTable> {
    constructor(private service: BookTableService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bookTable: HttpResponse<BookTable>) => bookTable.body));
        }
        return of(new BookTable());
    }
}

export const bookTableRoute: Routes = [
    {
        path: 'book-table',
        component: BookTableComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.bookTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-table/:id/view',
        component: BookTableDetailComponent,
        resolve: {
            bookTable: BookTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.bookTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-table/new',
        component: BookTableUpdateComponent,
        resolve: {
            bookTable: BookTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.bookTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book-table/:id/edit',
        component: BookTableUpdateComponent,
        resolve: {
            bookTable: BookTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.bookTable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookTablePopupRoute: Routes = [
    {
        path: 'book-table/:id/delete',
        component: BookTableDeletePopupComponent,
        resolve: {
            bookTable: BookTableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.bookTable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
