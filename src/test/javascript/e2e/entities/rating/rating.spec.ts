/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RatingComponentsPage, RatingDeleteDialog, RatingUpdatePage } from './rating.page-object';

const expect = chai.expect;

describe('Rating e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let ratingUpdatePage: RatingUpdatePage;
    let ratingComponentsPage: RatingComponentsPage;
    let ratingDeleteDialog: RatingDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Ratings', async () => {
        await navBarPage.goToEntity('rating');
        ratingComponentsPage = new RatingComponentsPage();
        expect(await ratingComponentsPage.getTitle()).to.eq('happybourBackEndApp.rating.home.title');
    });

    it('should load create Rating page', async () => {
        await ratingComponentsPage.clickOnCreateButton();
        ratingUpdatePage = new RatingUpdatePage();
        expect(await ratingUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.rating.home.createOrEditLabel');
        await ratingUpdatePage.cancel();
    });

    it('should create and save Ratings', async () => {
        const nbButtonsBeforeCreate = await ratingComponentsPage.countDeleteButtons();

        await ratingComponentsPage.clickOnCreateButton();
        await ratingUpdatePage.setCommentInput('comment');
        expect(await ratingUpdatePage.getCommentInput()).to.eq('comment');
        await ratingUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await ratingUpdatePage.getDateCreatedInput()).to.contain('2001-01-01T02:30');
        await ratingUpdatePage.setDateUpdatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await ratingUpdatePage.getDateUpdatedInput()).to.contain('2001-01-01T02:30');
        await ratingUpdatePage.setStarInput('5');
        expect(await ratingUpdatePage.getStarInput()).to.eq('5');
        await ratingUpdatePage.save();
        expect(await ratingUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await ratingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Rating', async () => {
        const nbButtonsBeforeDelete = await ratingComponentsPage.countDeleteButtons();
        await ratingComponentsPage.clickOnLastDeleteButton();

        ratingDeleteDialog = new RatingDeleteDialog();
        expect(await ratingDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.rating.delete.question');
        await ratingDeleteDialog.clickOnConfirmButton();

        expect(await ratingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
