/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HappybourBackEndTestModule } from '../../../test.module';
import { BookTableDeleteDialogComponent } from 'app/entities/book-table/book-table-delete-dialog.component';
import { BookTableService } from 'app/entities/book-table/book-table.service';

describe('Component Tests', () => {
    describe('BookTable Management Delete Component', () => {
        let comp: BookTableDeleteDialogComponent;
        let fixture: ComponentFixture<BookTableDeleteDialogComponent>;
        let service: BookTableService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [BookTableDeleteDialogComponent]
            })
                .overrideTemplate(BookTableDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BookTableDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookTableService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
