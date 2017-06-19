import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { UserMenuRel } from './user-menu-rel.model';
import { UserMenuRelPopupService } from './user-menu-rel-popup.service';
import { UserMenuRelService } from './user-menu-rel.service';

@Component({
    selector: 'jhi-user-menu-rel-dialog',
    templateUrl: './user-menu-rel-dialog.component.html'
})
export class UserMenuRelDialogComponent implements OnInit {

    userMenuRel: UserMenuRel;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private userMenuRelService: UserMenuRelService,
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
        if (this.userMenuRel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMenuRelService.update(this.userMenuRel), false);
        } else {
            this.subscribeToSaveResponse(
                this.userMenuRelService.create(this.userMenuRel), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<UserMenuRel>, isCreated: boolean) {
        result.subscribe((res: UserMenuRel) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserMenuRel, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'orderApp.userMenuRel.created'
            : 'orderApp.userMenuRel.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'userMenuRelListModification', content: 'OK'});
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
    selector: 'jhi-user-menu-rel-popup',
    template: ''
})
export class UserMenuRelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMenuRelPopupService: UserMenuRelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userMenuRelPopupService
                    .open(UserMenuRelDialogComponent, params['id']);
            } else {
                this.modalRef = this.userMenuRelPopupService
                    .open(UserMenuRelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
