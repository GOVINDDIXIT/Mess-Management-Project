from django.urls import path

from .views import verify_token

urlpatterns = [
    path('tokensignin/', verify_token, name="verify_token"),
]
