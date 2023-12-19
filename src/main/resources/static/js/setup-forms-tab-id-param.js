
        var forms = document.getElementsByTagName('form');
            for (var i = 0; i < forms.length; i++) {
                addHiddenTabIdField(forms[i]);
            }
        function addHiddenTabIdField(form) {
            var hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = 'tabId';
            hiddenField.value = sessionStorage.getItem('tabId');
            form.appendChild(hiddenField);
        };