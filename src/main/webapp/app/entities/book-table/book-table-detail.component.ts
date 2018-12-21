import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBookTable } from 'app/shared/model/book-table.model';

@Component({
    selector: 'jhi-book-table-detail',
    templateUrl: './book-table-detail.component.html'
})
export class BookTableDetailComponent implements OnInit {
    bookTable: IBookTable;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookTable }) => {
            this.bookTable = bookTable;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
