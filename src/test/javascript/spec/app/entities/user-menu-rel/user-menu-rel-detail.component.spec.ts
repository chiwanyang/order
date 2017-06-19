import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { OrderTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserMenuRelDetailComponent } from '../../../../../../main/webapp/app/entities/user-menu-rel/user-menu-rel-detail.component';
import { UserMenuRelService } from '../../../../../../main/webapp/app/entities/user-menu-rel/user-menu-rel.service';
import { UserMenuRel } from '../../../../../../main/webapp/app/entities/user-menu-rel/user-menu-rel.model';

describe('Component Tests', () => {

    describe('UserMenuRel Management Detail Component', () => {
        let comp: UserMenuRelDetailComponent;
        let fixture: ComponentFixture<UserMenuRelDetailComponent>;
        let service: UserMenuRelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrderTestModule],
                declarations: [UserMenuRelDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserMenuRelService,
                    EventManager
                ]
            }).overrideTemplate(UserMenuRelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMenuRelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMenuRelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserMenuRel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userMenuRel).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
