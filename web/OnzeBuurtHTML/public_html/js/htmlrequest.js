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
    request.open("GET", BASE_URL + "/onzebuurt/" + groups[selectedGroupIndex].id + "/");
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
function createGroupFromInput() {
    
    var group = {};
    group.title = jQuery.trim($("#groupTitle").val());
    
    if (group.title.length < 1) {
        $("#groupDialog .alert-error").text("A group's title cannot be empty").show();
        return;
    }
    
    for (var i = 0; i < groups.length; i++) {
        if (group.title === groups[i].title) {
            $("#groupDialog .alert-error").text("A group with this title already exists").show();
            return;
        }
    }
    
    // Send the new group to the back-end.
    var request = new XMLHttpRequest();
    request.open("POST", BASE_URL + "/groups");
    request.onload = function() {
        if (request.status === 201) {
            group.id = request.getResponseHeader("Location").split("/").pop();
            groups.push(group);
            $("#groupList").append(createListElementForGroup(groups.length - 1));
            selectGroupAndLoadReminders(groups.length - 1);
            $(".reminderDialogToggle").attr("disabled", false);
            $("#groupDialog").modal("hide");
        } else {
            $("#groupDialog .alert-error").text("Error creating group. See the console for more information.").show();
            console.log("Error creating group: " + request.status + " " + request.responseText);
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(group));
}

function updateGroupWithInput() {
    
    var group = jQuery.extend(true, {}, groups[selectedGroupIndex]);
    group.title = jQuery.trim($("#groupTitle").val());
    
    if (group.title.length < 1) {
        $("#groupDialog .alert-error").text("A group's title cannot be empty").show();
        return;
    }
    
    for (var i = 0; i < groups.length; i++) {
        if (i !== selectedGroupIndex && group.title === groups[i].title) {
            $("#groupDialog .alert-error").text("A group with this title already exists").show();
            return;
        }
    }

    // Send the updated group to the back-end.
    var request = new XMLHttpRequest();
    request.open("PUT", BASE_URL + "/groups/" + group.id);
    request.onload = function() {
        if (request.status === 204) {
            groups.splice(selectedGroupIndex, 1, group);
            var oldElement = $("#groupList li")[selectedGroupIndex];
            $(oldElement).replaceWith(createListElementForGroup(selectedGroupIndex));
            var newElement = $("#groupList li")[selectedGroupIndex];
            $(newElement).addClass("active");
            $(".icon-edit", newElement).show();
            $("#groupDialog").modal("hide");
        } else {
            $("#groupDialog .alert-error").text("Error creating group. See the console for more information.").show();
            console.log("Error creating group: " + request.status + " " + request.responseText);
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(group));
}

function deleteSelectedGroup() {
    
    // Send a delete request to the back-end.
    var request = new XMLHttpRequest();
    request.open("DELETE", BASE_URL + "/groups/" + groups[selectedGroupIndex].id);
    request.onload = function() {
        if (request.status === 204) {
            groups.splice(selectedGroupIndex, 1);
            
            // Rebuild the group list (otherwise the indices used in the list elements are off).
            $("#groupList").empty();
            for (var i = 0; i < groups.length; i++) {
                $("#groupList").append(createListElementForGroup(i));
            }
            
            if (selectedGroupIndex > 0) {
                selectGroupAndLoadReminders(selectedGroupIndex - 1);
            } else if (groups.length > 0) {
                selectGroupAndLoadReminders(0);
            } else {
                selectedGroupIndex = undefined;
                $(".reminderDialogToggle").attr("disabled", true);
            }
            $("#groupDialog").modal("hide");
        } else {
            console.log("Error deleting group: " + request.status + " - " + request.statusText);
        }
    };
    request.send(null);
}

