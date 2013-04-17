// Replace this URL with the URL where the back-end is hosted.
var BASE_URL = "http://localhost:8080/onzebuurt/resources";

var groups = [];
var reminders = [];

var selectedGroupIndex = undefined;
var editingGroup = false;

var selectedReminderIndex = undefined;
var editingReminder = false;

var map = undefined;
var marker = undefined;

onload = function() {
    
    initialiseLists();
    prepareGroupDialog();
    prepareReminderDialog();
    prepareMapDialog();
};

function initialiseLists() {
    
    // Load the groups from the back-end.
    var request = new XMLHttpRequest();
    request.open("GET", BASE_URL + "/situatie");
    request.onload = function() {
        if (request.status === 200) {
            groups = JSON.parse(request.responseText);
            for (var i = 0; i < groups.length; i++) {
                $("#groupList").append(createListElementForGroup(i));
            }
            
            if (groups.length > 0) {
                selectGroupAndLoadReminders(0);
            } else {
                $(".reminderDialogToggle").attr("disabled", true);
            }
        } else {
            console.log("Error loading groups: " + request.status + " - "+ request.statusText);
        }
    };
    request.send(null);
}
function prepareGroupDialog() {
    
    // This dialog can be used both for adding and editing groups.
    // By default it is set up for adding a new group.
    // When the dialog is closed, it is reset back to its default.
    
    $("#groupDialog .alert-error").hide();    
    $("#groupDialogDelete").hide();
    
    $("#groupDialog").on("hidden", function() {
        $("#groupDialog h3").text("Add Group");
        $("#groupTitle").val("");
        $("#groupDialog .alert-error").hide();
        $("#groupDialogDelete").hide();
    });
    
    $("#groupDialog").on("shown", function() {
        $("#groupTitle").focus();
    });
    
    $(".groupDialogToggle").click(function() {
        editingGroup = false;
        $("#groupDialog").modal("show");
    });
    
    $("#groupDialogDelete").click(function() {
        deleteSelectedGroup();
    });
    
    $("#groupDialogCancel").click(function() {
        $("#groupDialog").modal("hide");
    });
    
    $("#groupDialogSave").click(function() {
        if (editingGroup) {
            updateGroupWithInput();
        } else {
            createGroupFromInput();
        }
    });
    
    $("#groupTitle").keydown(function(event) {
        if(event.keyCode === 13){
            event.preventDefault();
            $("#groupDialogSave").click();
        }
    });
}
function selectGroupAndLoadReminders(groupIndex, selectedReminderId) {
    
    selectedGroupIndex = groupIndex;
    $("#groupList li").removeClass("active");
    $("#groupList .icon-edit").hide();

    var selectedElement = $("#groupList li")[selectedGroupIndex];
    $(selectedElement).addClass("active");
    $(".icon-edit", selectedElement).show();

    $("#reminderList").empty();

    // Load the reminders in this group from the back-end.
    var request = new XMLHttpRequest();
    request.open("GET", BASE_URL + "/groups/" + groups[selectedGroupIndex].id + "/reminders");
    request.onload = function() {
        if (request.status === 200) {
            reminders = JSON.parse(request.responseText);
            for (var i = 0; i < reminders.length; i++) {
                $("#reminderList").append(createListElementForReminder(i));
            }
            
            if (selectedReminderId !== undefined) {
                for (var i = 0; i < reminders.length; i++) {
                    if (reminders[i].id === selectedReminderId) {
                        selectReminder(i);
                        break;
                    }
                }
            } else if (reminders.length > 0) {
                selectReminder(0);
            }
        } else {
            console.log("Error loading reminders: " + request.status + " - " + request.statusText);
        }
    };
    request.send(null);
}