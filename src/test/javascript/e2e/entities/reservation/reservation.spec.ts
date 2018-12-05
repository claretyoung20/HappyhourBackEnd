/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReservationComponentsPage, ReservationDeleteDialog, ReservationUpdatePage } from './reservation.page-object';

const expect = chai.expect;

describe('Reservation e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let reservationUpdatePage: ReservationUpdatePage;
    let reservationComponentsPage: ReservationComponentsPage;
    let reservationDeleteDialog: ReservationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Reservations', async () => {
        await navBarPage.goToEntity('reservation');
        reservationComponentsPage = new ReservationComponentsPage();
        expect(await reservationComponentsPage.getTitle()).to.eq('happybourBackEndApp.reservation.home.title');
    });

    it('should load create Reservation page', async () => {
        await reservationComponentsPage.clickOnCreateButton();
        reservationUpdatePage = new ReservationUpdatePage();
        expect(await reservationUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.reservation.home.createOrEditLabel');
        await reservationUpdatePage.cancel();
    });

    it('should create and save Reservations', async () => {
        const nbButtonsBeforeCreate = await reservationComponentsPage.countDeleteButtons();

        await reservationComponentsPage.clickOnCreateButton();
        await reservationUpdatePage.setCommentInput('comment');
        expect(await reservationUpdatePage.getCommentInput()).to.eq('comment');
        await reservationUpdatePage.setStatusInput('status');
        expect(await reservationUpdatePage.getStatusInput()).to.eq('status');
        await reservationUpdatePage.setReserverDateInput('2000-12-31');
        expect(await reservationUpdatePage.getReserverDateInput()).to.eq('2000-12-31');
        await reservationUpdatePage.setUpdatedDateInput('2000-12-31');
        expect(await reservationUpdatePage.getUpdatedDateInput()).to.eq('2000-12-31');
        await reservationUpdatePage.setPeriodInput('period');
        expect(await reservationUpdatePage.getPeriodInput()).to.eq('period');
        await reservationUpdatePage.staffSelectLastOption();
        await reservationUpdatePage.bookTableSelectLastOption();
        await reservationUpdatePage.customerSelectLastOption();
        await reservationUpdatePage.save();
        expect(await reservationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await reservationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Reservation', async () => {
        const nbButtonsBeforeDelete = await reservationComponentsPage.countDeleteButtons();
        await reservationComponentsPage.clickOnLastDeleteButton();

        reservationDeleteDialog = new ReservationDeleteDialog();
        expect(await reservationDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.reservation.delete.question');
        await reservationDeleteDialog.clickOnConfirmButton();

        expect(await reservationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
