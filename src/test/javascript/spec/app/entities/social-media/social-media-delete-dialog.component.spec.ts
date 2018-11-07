/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HappybourBackEndTestModule } from '../../../test.module';
import { SocialMediaDeleteDialogComponent } from 'app/entities/social-media/social-media-delete-dialog.component';
import { SocialMediaService } from 'app/entities/social-media/social-media.service';

describe('Component Tests', () => {
    describe('SocialMedia Management Delete Component', () => {
        let comp: SocialMediaDeleteDialogComponent;
        let fixture: ComponentFixture<SocialMediaDeleteDialogComponent>;
        let service: SocialMediaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [SocialMediaDeleteDialogComponent]
            })
                .overrideTemplate(SocialMediaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SocialMediaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialMediaService);
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
