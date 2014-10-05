<h1> ${root.getName()} </h1>

<#if root.hasParent() >
    <a href="/dir/${root.getParent().getId()}">
        <input type="button" value="Back"/>
    </a>
</#if>

<table>
    <tr>
        <th>Name</th>
        <th>Format</th>
        <th>Option</th>
    </tr>

    <#list root.getChildren() as item>
        <tr>
            <td> ${item.getName()} </td>
            <td> ${item.getFormat()} </td>
            <td>
                <#if helper.isAFile(item)>
                    <#list item.getPermittedOperations() as operation>
                        <a href="/item/${item.getId()}/${operation}">
                            <input type="button" value="${operation}"/>
                        </a>
                    </#list>
                 <#else>
                     <a href="/dir/${item.getId()}">
                        <input type="button" value="Open"/>
                     </a>
                </#if>
            </td>
        </tr>
    </#list>

</table>