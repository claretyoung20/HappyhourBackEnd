/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ContactUsUpdateComponent } from 'app/entities/contact-us/contact-us-update.component';
import { ContactUsService } from 'app/entities/contact-us/contact-us.service';
import { ContactUs } from 'app/shared/model/contact-us.model';

describe('Component Tests', () => {
    describe('ContactUs Management Update Component', () => {
        let comp: ContactUsUpdateComponent;
        let fixture: ComponentFixture<ContactUsUpdateComponent>;
        let service: ContactUsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ContactUsUpdateComponent]
            })
                .overrideTemplate(ContactUsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContactUsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactUsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContactUs(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contactUs = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContactUs();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.contactUs = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
