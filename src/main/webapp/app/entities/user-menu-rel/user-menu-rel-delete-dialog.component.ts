import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { UserMenuRel } from './user-menu-rel.model';
import { UserMenuRelPopupService } from './user-menu-rel-popup.service';
import { UserMenuRelService } from './user-menu-rel.service';

@Component({
    selector: 'jhi-user-menu-rel-delete-dialog',
    templateUrl: './user-menu-rel-delete-dialog.component.html'
})
export class UserMenuRelDeleteDialogComponent {

    userMenuRel: UserMenuRel;

    constructor(
        private userMenuRelService: UserMenuRelService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMenuRelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userMenuRelListModification',
                content: 'Deleted an userMenuRel'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('orderApp.userMenuRel.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-user-menu-rel-delete-popup',
    template: ''
})
export class UserMenuRelDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMenuRelPopupService: UserMenuRelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userMenuRelPopupService
                .open(UserMenuRelDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
