/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TimeManagerComponentsPage, TimeManagerDeleteDialog, TimeManagerUpdatePage } from './time-manager.page-object';

const expect = chai.expect;

describe('TimeManager e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let timeManagerUpdatePage: TimeManagerUpdatePage;
    let timeManagerComponentsPage: TimeManagerComponentsPage;
    let timeManagerDeleteDialog: TimeManagerDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TimeManagers', async () => {
        await navBarPage.goToEntity('time-manager');
        timeManagerComponentsPage = new TimeManagerComponentsPage();
        expect(await timeManagerComponentsPage.getTitle()).to.eq('happybourBackEndApp.timeManager.home.title');
    });

    it('should load create TimeManager page', async () => {
        await timeManagerComponentsPage.clickOnCreateButton();
        timeManagerUpdatePage = new TimeManagerUpdatePage();
        expect(await timeManagerUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.timeManager.home.createOrEditLabel');
        await timeManagerUpdatePage.cancel();
    });

    it('should create and save TimeManagers', async () => {
        const nbButtonsBeforeCreate = await timeManagerComponentsPage.countDeleteButtons();

        await timeManagerComponentsPage.clickOnCreateButton();
        await timeManagerUpdatePage.setDayInput('day');
        expect(await timeManagerUpdatePage.getDayInput()).to.eq('day');
        await timeManagerUpdatePage.setStartTimeInput('startTime');
        expect(await timeManagerUpdatePage.getStartTimeInput()).to.eq('startTime');
        await timeManagerUpdatePage.setEndTimeInput('endTime');
        expect(await timeManagerUpdatePage.getEndTimeInput()).to.eq('endTime');
        await timeManagerUpdatePage.restaurantSelectLastOption();
        await timeManagerUpdatePage.save();
        expect(await timeManagerUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await timeManagerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TimeManager', async () => {
        const nbButtonsBeforeDelete = await timeManagerComponentsPage.countDeleteButtons();
        await timeManagerComponentsPage.clickOnLastDeleteButton();

        timeManagerDeleteDialog = new TimeManagerDeleteDialog();
        expect(await timeManagerDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.timeManager.delete.question');
        await timeManagerDeleteDialog.clickOnConfirmButton();

        expect(await timeManagerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
