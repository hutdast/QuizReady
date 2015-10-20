function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
      
    }
}


 function toStatus(display1, display2, display3) {
                                      
                                          PF(display1).hide();
                                           PF(display2).hide();
                                            PF(display3).show();
                                      
                                    }

