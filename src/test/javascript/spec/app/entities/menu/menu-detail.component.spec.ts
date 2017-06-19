import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { OrderTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MenuDetailComponent } from '../../../../../../main/webapp/app/entities/menu/menu-detail.component';
import { MenuService } from '../../../../../../main/webapp/app/entities/menu/menu.service';
import { Menu } from '../../../../../../main/webapp/app/entities/menu/menu.model';

describe('Component Tests', () => {

    describe('Menu Management Detail Component', () => {
        let comp: MenuDetailComponent;
        let fixture: ComponentFixture<MenuDetailComponent>;
        let service: MenuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrderTestModule],
                declarations: [MenuDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MenuService,
                    EventManager
                ]
            }).overrideTemplate(MenuDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MenuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Menu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.menu).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
