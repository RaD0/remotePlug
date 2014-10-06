<#include "/wraps/header.ftl">
<#include "/wraps/menu.ftl">

<div class="container">
    <#include "/partials/currently_running.ftl">

    <div class="row">
        <div class="col-lg-12 text-center">
            <h1>
                <div class="alert alert-success">
                    <#assign item=root>
                    <#include "/partials/back.ftl">
                    ${root.getName()}
                </div>
            </h1>
            <div class="row">
                <#include "/partials/items_table.ftl">
            </div>
        </div>
    </div>
</div>

<#include "/wraps/footer.ftl">