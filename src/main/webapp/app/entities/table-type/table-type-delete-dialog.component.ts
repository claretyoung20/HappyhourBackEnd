import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITableType } from 'app/shared/model/table-type.model';
import { TableTypeService } from './table-type.service';

@Component({
    selector: 'jhi-table-type-delete-dialog',
    templateUrl: './table-type-delete-dialog.component.html'
})
export class TableTypeDeleteDialogComponent {
    tableType: ITableType;

    constructor(private tableTypeService: TableTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tableTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tableTypeListModification',
                content: 'Deleted an tableType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-table-type-delete-popup',
    template: ''
})
export class TableTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tableType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TableTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tableType = tableType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
