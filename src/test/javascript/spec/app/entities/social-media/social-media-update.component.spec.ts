/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { SocialMediaUpdateComponent } from 'app/entities/social-media/social-media-update.component';
import { SocialMediaService } from 'app/entities/social-media/social-media.service';
import { SocialMedia } from 'app/shared/model/social-media.model';

describe('Component Tests', () => {
    describe('SocialMedia Management Update Component', () => {
        let comp: SocialMediaUpdateComponent;
        let fixture: ComponentFixture<SocialMediaUpdateComponent>;
        let service: SocialMediaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [SocialMediaUpdateComponent]
            })
                .overrideTemplate(SocialMediaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SocialMediaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialMediaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SocialMedia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.socialMedia = entity;
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
                    const entity = new SocialMedia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.socialMedia = entity;
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
