import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookTable } from 'app/shared/model/book-table.model';

@Component({
    selector: 'jhi-book-table-detail',
    templateUrl: './book-table-detail.component.html'
})
export class BookTableDetailComponent implements OnInit {
    bookTable: IBookTable;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bookTable }) => {
            this.bookTable = bookTable;
        });
    }

    previousState() {
        window.history.back();
    }
}
