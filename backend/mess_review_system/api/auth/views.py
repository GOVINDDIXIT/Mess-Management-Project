from django.conf import settings
from django.http import JsonResponse

from rest_framework.decorators import api_view

from google.oauth2 import id_token
from google.auth.transport import requests


@api_view(['POST'])
def verify_token(request):
    "Verifies The ID Token Sent By The App If User Successfully Signed In"

    token = request.POST.get('token')
    try:
        idinfo = id_token.verify_oauth2_token(
            token, requests.Request(), settings.GOOGLE_APP_CLIENT_ID)

        if idinfo['iss'] not in ['accounts.google.com', 'https://accounts.google.com']:
            raise ValueError('Wrong issuer.')

        # ID token is valid. Get the user's Google Account ID from the decoded token.
        userid = idinfo['sub']
        return userid

    except ValueError:
        return JsonResponse({'status': 'false', 'detail': 'Invalid Token ID'}, status=503)
