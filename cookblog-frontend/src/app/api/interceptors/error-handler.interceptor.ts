import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { NotificationService } from '../../shared/services/notification.service';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  private static readonly BAD_REQUEST_STATUS = 400;
  private static readonly UNAUTHENTICATED_STATUS = 401;
  private static readonly FORBIDDEN_STATUS = 403;
  private static readonly UNAUTHENTICATED_MESSAGE = 'Błąd autoryzacji';
  private static readonly FORBIDDEN_MESSAGE = 'Odmowa dostępu';
  private static readonly SERVER_ERROR_MESSAGE = 'Wystąpił nieoczekiwany błąd';

  constructor(private readonly notificationService: NotificationService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        this.showErrorNotification(error);
        return throwError(() => error);
      }),
    );
  }

  private showErrorNotification(errorResponse: HttpErrorResponse) {
    switch (errorResponse.status) {
      case ErrorHandlerInterceptor.BAD_REQUEST_STATUS:
        return this.showBadRequestNotification(errorResponse);
      case ErrorHandlerInterceptor.UNAUTHENTICATED_STATUS:
        return this.showUnauthenticatedNotification();
      case ErrorHandlerInterceptor.FORBIDDEN_STATUS:
        return this.showForbiddenNotification();
      default:
        return this.showServerErrorNotification();
    }
  }

  private showBadRequestNotification(errorResponse: HttpErrorResponse) {
    const errorMessage = errorResponse.error.errors[0];
    this.notificationService.showError(errorMessage);
  }

  private showUnauthenticatedNotification() {
    this.notificationService.showError(
      ErrorHandlerInterceptor.UNAUTHENTICATED_MESSAGE,
    );
  }

  private showForbiddenNotification() {
    this.notificationService.showError(
      ErrorHandlerInterceptor.FORBIDDEN_MESSAGE,
    );
  }

  private showServerErrorNotification() {
    this.notificationService.showError(
      ErrorHandlerInterceptor.SERVER_ERROR_MESSAGE,
    );
  }
}
