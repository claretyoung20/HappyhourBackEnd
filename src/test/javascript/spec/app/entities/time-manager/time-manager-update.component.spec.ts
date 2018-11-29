/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { TimeManagerUpdateComponent } from 'app/entities/time-manager/time-manager-update.component';
import { TimeManagerService } from 'app/entities/time-manager/time-manager.service';
import { TimeManager } from 'app/shared/model/time-manager.model';

describe('Component Tests', () => {
    describe('TimeManager Management Update Component', () => {
        let comp: TimeManagerUpdateComponent;
        let fixture: ComponentFixture<TimeManagerUpdateComponent>;
        let service: TimeManagerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [TimeManagerUpdateComponent]
            })
                .overrideTemplate(TimeManagerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeManagerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeManagerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimeManager(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeManager = entity;
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
                    const entity = new TimeManager();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeManager = entity;
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
