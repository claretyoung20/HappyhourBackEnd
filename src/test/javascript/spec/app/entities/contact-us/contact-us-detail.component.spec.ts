/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ContactUsDetailComponent } from 'app/entities/contact-us/contact-us-detail.component';
import { ContactUs } from 'app/shared/model/contact-us.model';

describe('Component Tests', () => {
    describe('ContactUs Management Detail Component', () => {
        let comp: ContactUsDetailComponent;
        let fixture: ComponentFixture<ContactUsDetailComponent>;
        const route = ({ data: of({ contactUs: new ContactUs(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ContactUsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContactUsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContactUsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contactUs).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
