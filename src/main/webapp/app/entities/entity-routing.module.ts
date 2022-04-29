import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'subscription-plan',
        data: { pageTitle: 'iifDirectPayApp.subscriptionPlan.home.title' },
        loadChildren: () => import('./subscription-plan/subscription-plan.module').then(m => m.SubscriptionPlanModule),
      },
      {
        path: 'organization',
        data: { pageTitle: 'iifDirectPayApp.organization.home.title' },
        loadChildren: () => import('./organization/organization.module').then(m => m.OrganizationModule),
      },
      {
        path: 'organization-subscription-plan',
        data: { pageTitle: 'iifDirectPayApp.organizationSubscriptionPlan.home.title' },
        loadChildren: () =>
          import('./organization-subscription-plan/organization-subscription-plan.module').then(m => m.OrganizationSubscriptionPlanModule),
      },
      {
        path: 'user-organization',
        data: { pageTitle: 'iifDirectPayApp.userOrganization.home.title' },
        loadChildren: () => import('./user-organization/user-organization.module').then(m => m.UserOrganizationModule),
      },
      {
        path: 'user-login',
        data: { pageTitle: 'iifDirectPayApp.userLogin.home.title' },
        loadChildren: () => import('./user-login/user-login.module').then(m => m.UserLoginModule),
      },
      {
        path: 'user-device',
        data: { pageTitle: 'iifDirectPayApp.userDevice.home.title' },
        loadChildren: () => import('./user-device/user-device.module').then(m => m.UserDeviceModule),
      },
      {
        path: 'user-account',
        data: { pageTitle: 'iifDirectPayApp.userAccount.home.title' },
        loadChildren: () => import('./user-account/user-account.module').then(m => m.UserAccountModule),
      },
      {
        path: 'receipient',
        data: { pageTitle: 'iifDirectPayApp.receipient.home.title' },
        loadChildren: () => import('./receipient/receipient.module').then(m => m.ReceipientModule),
      },
      {
        path: 'receipient-recurring',
        data: { pageTitle: 'iifDirectPayApp.receipientRecurring.home.title' },
        loadChildren: () => import('./receipient-recurring/receipient-recurring.module').then(m => m.ReceipientRecurringModule),
      },
      {
        path: 'patron',
        data: { pageTitle: 'iifDirectPayApp.patron.home.title' },
        loadChildren: () => import('./patron/patron.module').then(m => m.PatronModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'iifDirectPayApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
