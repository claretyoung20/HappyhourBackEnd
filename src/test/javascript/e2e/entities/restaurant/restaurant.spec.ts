/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RestaurantComponentsPage, RestaurantDeleteDialog, RestaurantUpdatePage } from './restaurant.page-object';

const expect = chai.expect;

describe('Restaurant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let restaurantUpdatePage: RestaurantUpdatePage;
    let restaurantComponentsPage: RestaurantComponentsPage;
    let restaurantDeleteDialog: RestaurantDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Restaurants', async () => {
        await navBarPage.goToEntity('restaurant');
        restaurantComponentsPage = new RestaurantComponentsPage();
        expect(await restaurantComponentsPage.getTitle()).to.eq('happybourBackEndApp.restaurant.home.title');
    });

    it('should load create Restaurant page', async () => {
        await restaurantComponentsPage.clickOnCreateButton();
        restaurantUpdatePage = new RestaurantUpdatePage();
        expect(await restaurantUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.restaurant.home.createOrEditLabel');
        await restaurantUpdatePage.cancel();
    });

    it('should create and save Restaurants', async () => {
        const nbButtonsBeforeCreate = await restaurantComponentsPage.countDeleteButtons();

        await restaurantComponentsPage.clickOnCreateButton();
        await restaurantUpdatePage.setCodeInput('code');
        expect(await restaurantUpdatePage.getCodeInput()).to.eq('code');
        const selectedIsActive = restaurantUpdatePage.getIsActiveInput();
        if (await selectedIsActive.isSelected()) {
            await restaurantUpdatePage.getIsActiveInput().click();
            expect(await restaurantUpdatePage.getIsActiveInput().isSelected()).to.be.false;
        } else {
            await restaurantUpdatePage.getIsActiveInput().click();
            expect(await restaurantUpdatePage.getIsActiveInput().isSelected()).to.be.true;
        }
        await restaurantUpdatePage.setNameInput('name');
        expect(await restaurantUpdatePage.getNameInput()).to.eq('name');
        await restaurantUpdatePage.save();
        expect(await restaurantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Restaurant', async () => {
        const nbButtonsBeforeDelete = await restaurantComponentsPage.countDeleteButtons();
        await restaurantComponentsPage.clickOnLastDeleteButton();

        restaurantDeleteDialog = new RestaurantDeleteDialog();
        expect(await restaurantDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.restaurant.delete.question');
        await restaurantDeleteDialog.clickOnConfirmButton();

        expect(await restaurantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
