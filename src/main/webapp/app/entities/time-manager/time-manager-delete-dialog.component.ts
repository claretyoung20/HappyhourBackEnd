import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeManager } from 'app/shared/model/time-manager.model';
import { TimeManagerService } from './time-manager.service';

@Component({
    selector: 'jhi-time-manager-delete-dialog',
    templateUrl: './time-manager-delete-dialog.component.html'
})
export class TimeManagerDeleteDialogComponent {
    timeManager: ITimeManager;

    constructor(
        private timeManagerService: TimeManagerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeManagerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeManagerListModification',
                content: 'Deleted an timeManager'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-manager-delete-popup',
    template: ''
})
export class TimeManagerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ timeManager }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimeManagerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.timeManager = timeManager;
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
