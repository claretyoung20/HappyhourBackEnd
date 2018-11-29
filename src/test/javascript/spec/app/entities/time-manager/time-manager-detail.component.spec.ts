/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { TimeManagerDetailComponent } from 'app/entities/time-manager/time-manager-detail.component';
import { TimeManager } from 'app/shared/model/time-manager.model';

describe('Component Tests', () => {
    describe('TimeManager Management Detail Component', () => {
        let comp: TimeManagerDetailComponent;
        let fixture: ComponentFixture<TimeManagerDetailComponent>;
        const route = ({ data: of({ timeManager: new TimeManager(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [TimeManagerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimeManagerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeManagerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timeManager).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
