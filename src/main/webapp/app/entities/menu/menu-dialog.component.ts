import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Menu } from './menu.model';
import { MenuPopupService } from './menu-popup.service';
import { MenuService } from './menu.service';

@Component({
    selector: 'jhi-menu-dialog',
    templateUrl: './menu-dialog.component.html'
})
export class MenuDialogComponent implements OnInit {

    menu: Menu;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private menuService: MenuService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.menuService.update(this.menu), false);
        } else {
            this.subscribeToSaveResponse(
                this.menuService.create(this.menu), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Menu>, isCreated: boolean) {
        result.subscribe((res: Menu) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Menu, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'orderApp.menu.created'
            : 'orderApp.menu.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'menuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-menu-popup',
    template: ''
})
export class MenuPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private menuPopupService: MenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.menuPopupService
                    .open(MenuDialogComponent, params['id']);
            } else {
                this.modalRef = this.menuPopupService
                    .open(MenuDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
