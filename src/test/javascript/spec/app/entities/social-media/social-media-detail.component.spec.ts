/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { SocialMediaDetailComponent } from 'app/entities/social-media/social-media-detail.component';
import { SocialMedia } from 'app/shared/model/social-media.model';

describe('Component Tests', () => {
    describe('SocialMedia Management Detail Component', () => {
        let comp: SocialMediaDetailComponent;
        let fixture: ComponentFixture<SocialMediaDetailComponent>;
        const route = ({ data: of({ socialMedia: new SocialMedia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [SocialMediaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SocialMediaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SocialMediaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.socialMedia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
